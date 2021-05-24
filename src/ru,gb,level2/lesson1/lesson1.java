import entity.Cat;
import entity.Human;
import entity.Motion;
import entity.Robot;
import obstacles.Obstacles;
import obstacles.Treadmill;
import obstacles.Wall;

import java.util.Random;

public class lesson1 {
    public static Motion[] motions;
    public static Obstacles[] obstacles;

    public static void main(String[] args) {
        Motion human = new Human("Vasya", 1020, 30);
        Motion cat = new Cat("Kuzya", 5600, 50);
        Motion robot = new Robot("Andromeda", 4000, 10);

        Random random = new Random();

        Wall wall = new Wall(random.nextInt(30));
        Treadmill treadmill = new Treadmill(random.nextInt(3000));

        boolean yes;
        System.out.println("Старт");
        for (Motion motion : motions
        ) {
            yes = allObstalces(motion);
            if (!yes) System.out.println(motion + " выбыл ");
            else System.out.println(motion + " прошел ");


        }
    }

    public void setMotions(Motion... motions) {
        lesson1.motions = motions;
    }

    public void setObstacles(Obstacles... obstacles) {
        lesson1.obstacles = obstacles;
    }

    public static boolean allObstalces(Motion motion) {
        for (Obstacles obstacle : obstacles
        ) {
            if (!obstacle.pastObstacles(motion)) {
                return false;
            }
            System.out.println();
        }
        return true;
    }
}
