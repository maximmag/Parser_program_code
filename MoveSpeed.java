
class NewClass{
    public static void main(String[] args){
        NewClass newClass = new NewClass();
        newClass.method1();
    }
    void method1(){
        Flora snake = new Snake();
        Flora human = new Human();
        snake.getdlinnaTela();
        snake.getmassaTela();
        human.getmassaTela();
        human.getdlinnaTela();
        snake.run();
        human.run();
        System.out.println("Скорость передвижения змеи = "+snake.moveSpeed);
        System.out.println("Скорость передвижения человека = "+human.moveSpeed);
    }

}

abstract class Flora{
    int dlinnaTela;
    int massaTela;
    int moveSpeed;
    void run(){
        moveSpeed = massaTela + dlinnaTela;
    }
    abstract void getdlinnaTela();
    abstract void getmassaTela();

}


class Snake extends Flora {
    void getdlinnaTela() {
        dlinnaTela = 40;
    }
    void getmassaTela(){
        massaTela = 5;
    }
}

class Human extends Flora {

    void getdlinnaTela() {
        dlinnaTela = 175;
    }

    void getmassaTela() {
        massaTela = 5;
    }
}
