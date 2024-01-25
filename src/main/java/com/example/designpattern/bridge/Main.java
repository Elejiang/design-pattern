package com.example.designpattern.bridge;

class Main {
    public static void main(String[] args) {
        AppStore appStore = new AppStore();
        Game game = new Game();
        Oppo oppo = new Oppo();
        Vivo vivo = new Vivo();
        oppo.setSoftware(appStore);
        oppo.run();
        oppo.setSoftware(game);
        oppo.run();
        vivo.setSoftware(appStore);
        vivo.run();
        vivo.setSoftware(game);
        vivo.run();
    }
}
