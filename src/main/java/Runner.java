import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hwMenu");
        EntityManager em = emf.createEntityManager();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Utils.initiateMenu(em);

            while (true) {
                System.out.println("\n1. Вывести меню на экран");
                System.out.println("2. Добавить/удалить блюда");
                System.out.println("3. Выбрать по критериям");
                System.out.println("4. Сформировать меню до 1кг.");
                System.out.print("-> ");

                String line = reader.readLine();

                switch (line) {
                    case ("1"):
                        Utils.listMenu(em);
                        break;
                    case ("2"):
                        Utils.editMenu(em, reader);
                        break;
                    case ("3"):
                        Utils.selectByCriteria(em, reader);
                        break;
                    case ("4"):
                        Utils.getRandomMenu(em);
                        break;
                    default:
                        return;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

}

