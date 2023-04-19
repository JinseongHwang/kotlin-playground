package lec10;

public final class JavaPenguin extends JavaAnimal implements lec10.JavaSwimable, JavaFlyable {

    private final int wingCount;

    public JavaPenguin(String species) {
        super(species, 2);
        this.wingCount = 2;
    }

    @Override
    public void move() {
        System.out.println("펭귄이 움직입니다~ 꿱꿱");
    }

    @Override
    public int getLegCount() {
        return super.legCount + this.wingCount;
    }

    @Override
    public void act() {
        lec10.JavaSwimable.super.act();
        JavaFlyable.super.act();
    }

}
