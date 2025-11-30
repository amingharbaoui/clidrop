public class Main {

    public static void main(String[] args) throws Exception {
        ServerManager manager = new ServerManager();
        manager.start();
        System.out.println("Server Started on:" + manager.getUrl());
    }
}


