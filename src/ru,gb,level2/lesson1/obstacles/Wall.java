package obstacles;

import entity.Motion;

public class Wall implements Obstacles {
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean pastObstacles(Motion motion) {
        if (motion.jump() >= height) {
            System.out.println(motion + "перепрыгнул через стену");
            return true;
        } else {
            System.out.println(motion + "не смог преодолеть стену высотой = " + height);
            return false;
        }
    }
}
