public class Main {
    public static double summ =0;
    public static void main(String[] args) throws InterruptedException {
        MyThread thread1 = new MyThread("+");
        MyThread thread2 = new MyThread("-");
        thread1.start();
        thread2.start();

    }

    public static final Object KEY = new Object();
    public static void test(String message) {

        synchronized(KEY) {
            try {
                Thread.sleep(3000);
                if (message=="+"){
                    double pl = 400+ Math.random()*1020;
                    summ = summ + pl;
                    String s1 = String.format("%.2f",summ);
                    String p1 = String.format("%.2f",pl);
                    System.out.println("На вашем балансе стало "+ s1 + "; Добавлено "+p1 + " Рублей");
                }
                else {
                    double mi = 400 + Math.random()*1180;
                    if (summ - mi <0){
                        String m1 = String.format("%.2f",mi);
                        System.out.println("Недостоточно средств для снятия "+ m1 + " Рублей");
                    }
                    else{
                        summ = summ - mi;
                        String s1 = String.format("%.2f",summ);
                        String m1 = String.format("%.2f",mi);
                        System.out.println("На вашем балансе осталось "+ s1 + "; Снято "+m1 + " Рублей");
                    }
                }
                Thread.sleep(3000);
                KEY.notify(); // возобновляем поток, наход. в режиме ожидания
                KEY.wait(); // выставляем в режим ожидания
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class MyThread extends Thread {
    String mess;
    boolean flag = true;
    MyThread(String m){
        mess = m;
    }
    @Override
    public void run() {
        while(flag) {
            Main.test(this.mess);
        }
    }
}