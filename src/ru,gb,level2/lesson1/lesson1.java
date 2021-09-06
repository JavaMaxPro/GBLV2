import entity.Motion;
import obstacles.Obstacles;

public class lesson1 {
    public static Motion[] motions;
    public static Obstacles[] obstacles;


    public static void main(String[] args) {

    }

   /* public void setMotions(Motion... motions) {
        lesson1.motions = motions;
    }

    public void setObstacles(Obstacles... obstacles) {
        lesson1.obstacles = obstacles;
    }*/

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
