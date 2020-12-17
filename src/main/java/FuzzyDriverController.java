import net.sourceforge.jFuzzyLogic.FIS;

public class FuzzyDriverController {

    private final FIS fis;
    private final boolean showGraphs = false;

    public FuzzyDriverController(String fuzzyDriverPath){
        fis = FIS.load(fuzzyDriverPath, false);
        if( fis == null ) {
            System.err.println("Can't load file: '" + fuzzyDriverPath + "'");
        }
        if(showGraphs) {
            fis.getFuzzyRuleSet().chart();
        }
    }

    public double getSpeedChange(int currentSpeed, double distanceFromVehicle, double slipperiness){
        fis.getFuzzyRuleSet().setVariable("speed", currentSpeed);
        fis.getFuzzyRuleSet().setVariable("distance", distanceFromVehicle);
        fis.getFuzzyRuleSet().setVariable("slipperiness", slipperiness);
        fis.getFuzzyRuleSet().evaluate();
        return fis.getFuzzyRuleSet().getVariable("speed_difference").defuzzify();
    }
}
