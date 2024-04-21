
public class BlackJackFactory {
    public BlackJackFactory(){

    }
    public BlackJackModel getModel(){
        return new BlackJackModel();
    }
    public BlackJackView getView(BlackJackModel model){
        return new BlackJackView(model);
    }
    public BlackJackController getController(BlackJackView view, BlackJackModel model){
        return new BlackJackController(model, view);
    }
}
