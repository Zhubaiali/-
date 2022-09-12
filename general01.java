public class general01 {
    public static void main(String[] args) {
        person 唐僧 = new person("唐僧", null);
        唐僧.通常情况();
        唐僧.过河();
        唐僧.过火焰山();
    }

}

interface vehicle {
    void work();
}

class horse implements vehicle {
    public void work() {
        System.out.println("一般骑马");
    }
}

class boat implements vehicle {
    public void work() {
        System.out.println("过河坐船");
    }
}

class 飞机 implements vehicle {
    public void work() {
        System.out.println("过火焰山坐飞机");
    }
}

class person {
    private String name;
    private vehicle 交通工具;

    public person(String name, vehicle 交通工具) {
        this.name = name;
        this.交通工具 = 交通工具;
    }

    public void 过火焰山() {
        if (!(交通工具 instanceof 飞机)) {
            交通工具 = vehicleFactory.get飞机();
        }
        交通工具.work();
    }

    public void 过河() {
        // boat 船 = vehicleFactory.getBoat();
        // 船.work();
        if (!(交通工具 instanceof boat)) {
            交通工具 = vehicleFactory.getBoat();
        }
        交通工具.work();
    }

    public void 通常情况() {
        if (交通工具 == null) {

            // 这里是多态，左边的“交通工具”是vehicle类型，右边getHorse()返回的是horse类型
            交通工具 = vehicleFactory.getHorse();
        }
        交通工具.work();
    }
}

class vehicleFactory {
    private static horse horse = new horse();// 饿汉式单例模式

    public static horse getHorse() {
        return horse;
    }// 因为马每次都是同一匹，所以不需要每次都new一个新的对象

    public static boat getBoat() {
        return new boat();
    }// 返回船对象

    public static 飞机 get飞机() {
        return new 飞机();
    }// 返回飞机对象
}
