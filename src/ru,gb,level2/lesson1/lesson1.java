import entity.Motion;
import obstacles.Obstacles;

public class lesson1 {
    public  static  Motion[] motions ;
    public  static Obstacles [] obstacles;
    public static void main(String[] args) {
        boolean yes;
        System.out.println("Старт");
        for (Motion motion:motions
             ) {
            yes = allObstalces(motion);
            if(!yes) System.out.println(motion + " выбыл ");
            else System.out.println(motion + " прошел ");

            
        }
    }
    public void setMotions(Motion... motions) {
        lesson1.motions = motions;
    }
    public void setObstacles(Obstacles... obstacles){
        lesson1.obstacles =obstacles;
    }

    public static boolean allObstalces(Motion motion){
        for (Obstacles obstacle:obstacles
             ) {
            if(!obstacle.pastObstacles(motion)){
                return  false;
            }
            System.out.println();
        }
        return true;
    }
}
