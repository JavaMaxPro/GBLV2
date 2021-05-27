package obstacles;

import entity.Motion;

public class Treadmill implements Obstacles {
    private int lenght;

    public Treadmill(int lenght) {
        this.lenght = lenght;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    @Override
    public boolean pastObstacles(Motion motion) {
        if (motion.run() >= lenght) {
            System.out.println(motion + "пробежал дистанцию длиной " + lenght);
            return true;
        } else {
            System.out.println(motion + "не смог преодолеть дистанцию длиной = " + lenght);
            return false;
        }
    }
}
