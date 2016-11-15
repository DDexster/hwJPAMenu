import Entity.Menu;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Utils {
    static void initiateMenu(EntityManager em) {
        em.getTransaction().begin();
        List<Menu> menu = new ArrayList<>();
        menu.add(new Menu("Салат", "Винигрет с тюлькой", 75, 250, false));
        menu.add(new Menu("Салат", "Гетьман с чесночными гренками", 119, 250, false));
        menu.add(new Menu("Салат", "Оливье с  индюшкой", 86, 250, true));
        menu.add(new Menu("Салат", "Селедка под шубой", 76, 250, false));
        menu.add(new Menu("Салат", "Печеные баклажаны с брынзой", 129, 200, true));
        menu.add(new Menu("Первое", "Суп с куринными требухами", 65, 330, false));
        menu.add(new Menu("Первое", "Бульйон с лапшой", 75, 330, true));
        menu.add(new Menu("Первое", "Борщ с пампушками", 75, 430, false));
        menu.add(new Menu("Первое", "Юшка с грибами", 116, 340, true));
        menu.add(new Menu("Первое", "Журек с грибами", 96, 300, false));
        menu.add(new Menu("Основные", "Судак со шпинатом", 248, 250, true));
        menu.add(new Menu("Основные", "Ципленок табака", 245, 400, false));
        menu.add(new Menu("Основные", "Котлета по-киевски", 114, 350, true));
        menu.add(new Menu("Основные", "Сковородка с мясом", 176, 450, false));
        menu.add(new Menu("Основные", "Кролик", 265, 320, false));
        menu.add(new Menu("Гарнир", "Гречневая каша", 86, 250, true));
        menu.add(new Menu("Гарнир", "Рис с овощами", 56, 250, false));
        menu.add(new Menu("Гарнир", "Банош с брынзой", 74, 280, true));
        menu.add(new Menu("Гарнир", "Жаренная картошка", 145, 600, false));
        menu.add(new Menu("Гарнир", "Брокколи в соусе", 79, 250, true));
        menu.add(new Menu("Закуски", "Крученики в сыром и зеленью", 215, 250, false));
        menu.add(new Menu("Закуски", "Деруны с курицей", 98, 300, true));
        menu.add(new Menu("Закуски", "Белые грибы в сметанном соусе", 164, 250, false));
        menu.add(new Menu("Закуски", "Кровянка с луком", 78, 150, true));
        menu.add(new Menu("Закуски", "Голубцы", 107, 400, false));
        menu.add(new Menu("Напитки", "Морс с клюквы", 22, 200, false));
        menu.add(new Menu("Напитки", "Квас", 24, 200, true));
        menu.add(new Menu("Напитки", "Узвар", 22, 200, true));
        menu.add(new Menu("Напитки", "Боржоми", 63, 500, false));
        menu.add(new Menu("Напитки", "Кофе", 40, 200, false));

        try {
            for (Menu dish : menu) {
                em.persist(dish);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    static void listMenu(EntityManager em) {
        List<Menu> menu = getMenu(em);

        listDishes(menu);
    }

    private static List<Menu> getMenu(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM Menu m", Menu.class);
        return (List<Menu>) query.getResultList();
    }

    private static void listDishes(List<Menu> menu) {
        for (Menu dish : menu) {
            System.out.println(dish);
        }
    }

    static void editMenu(EntityManager em, BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("\n1. Добавить блюдо в меню");
            System.out.println("2. Удалить блюдо из меню");

            String line = reader.readLine();

            switch (line) {
                case ("1"):
                    addDish(em, reader);
                    return;
                case ("2"):
                    removeDish(em, reader);
                    return;
                default:
                    return;
            }
        }
    }

    private static void addDish(EntityManager em, BufferedReader reader) throws IOException {
        System.out.println("\nВведите категорию блюда:");
        String category = reader.readLine();
        System.out.println("Введите название блюда:");
        String name = reader.readLine();
        System.out.println("Введите выходной вес блюда:");
        Double weight = Double.parseDouble(reader.readLine());
        System.out.println("Введите цену блюда:");
        Double price = Double.parseDouble(reader.readLine());
        System.out.println("Хотите установить скидку на это блюдо? (y/n)");
        boolean discount = reader.readLine().equalsIgnoreCase("y") ? true : false;
        Menu dish = new Menu(category, name, price, weight, discount);
        persistObject(em, dish);
        System.out.println("Блюдо добавлено!");
    }

    private static void persistObject(EntityManager em, Object object) {
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void removeDish(EntityManager em, BufferedReader reader) throws IOException {
        System.out.println("\nВведите ИД блюда, которое хотите удалить:");
        Integer id = Integer.parseInt(reader.readLine());
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("DELETE FROM Menu WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            em.getTransaction().commit();
            System.out.println("Блюдо удалено");
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    static void selectByCriteria(EntityManager em, BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("\n1. Выбрать блюда по отрезку стоимости");
            System.out.println("2. Выбрать блюда по наличию скидки");
            String line = reader.readLine();

            switch (line) {
                case ("1"):
                    selectByPrice(em, reader);
                    return;
                case ("2"):
                    selectByDiscount(em);
                    return;
                default:
                    return;
            }
        }
    }

    private static void selectByPrice(EntityManager em, BufferedReader reader) throws IOException {
        System.out.println("Введите нижнюю границу цены:");
        Double from = Double.parseDouble(reader.readLine());
        System.out.println("Введите верхнюю границу цены:");
        Double to = Double.parseDouble(reader.readLine());

        Query query = em.createQuery("SELECT m FROM Menu m WHERE m.price > :from AND m.price < :to");
        query.setParameter("from", from);
        query.setParameter("to", to);
        List<Menu> result = query.getResultList();
        listDishes(result);
    }

    private static void selectByDiscount(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM Menu m WHERE m.isDiscount = true");
        List<Menu> result = query.getResultList();
        listDishes(result);
    }

    static void getRandomMenu(EntityManager em) {
        Random random = new Random();
        Map<String, List<Menu>> categorizedMenu = getCategorizedMenu(em);
        List<Menu> randMenu;
        while (true) {
            double totalWeight = 0;
            randMenu = new ArrayList<>();
            String soupS = "Первое";
            Menu soup = categorizedMenu.get(soupS).get(random.nextInt(categorizedMenu.get(soupS).size()));
            String snacksS = "Закуски";
            Menu snacks = categorizedMenu.get(snacksS).get(random.nextInt(categorizedMenu.get(snacksS).size()));
            String saladS = "Салат";
            Menu salad = categorizedMenu.get(saladS).get(random.nextInt(categorizedMenu.get(saladS).size()));
            String drinkS = "Напитки";
            Menu drink = categorizedMenu.get(drinkS).get(random.nextInt(categorizedMenu.get(drinkS).size()));
            randMenu.add(soup);
            randMenu.add(snacks);
            randMenu.add(salad);
            randMenu.add(drink);
            totalWeight = soup.getWeight() + snacks.getWeight() + salad.getWeight() + drink.getWeight();
            if (totalWeight <= 1000) break;
        }
        listDishes(randMenu);
    }

    static Map<String, List<Menu>> getCategorizedMenu(EntityManager em) {
        Map<String, List<Menu>> categorizedMenu = new HashMap<>();
        List<Menu> menu = getMenu(em);
        for (Menu dish : menu) {
            if (!categorizedMenu.containsKey(dish.getCategory())) {
                String key = dish.getCategory();
                List<Menu> dishes = new ArrayList<>();
                dishes.add(dish);
                categorizedMenu.put(key, dishes);
            } else {
                categorizedMenu.get(dish.getCategory()).add(dish);
            }
        }
        return categorizedMenu;
    }
}
