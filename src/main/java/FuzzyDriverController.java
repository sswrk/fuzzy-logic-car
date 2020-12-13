import net.sourceforge.jFuzzyLogic.FIS;

public class FuzzyDriverController {

    public FIS fis;

    public FuzzyDriverController(String fuzzyDriverPath){
        fis = FIS.load(fuzzyDriverPath, false);
        if( fis == null ) {
            System.err.println("Can't load file: '" + fuzzyDriverPath + "'");
        }
    }

    public double getSpeedChange(int currentSpeed, double distanceFromVehicle, double slipperiness){
        fis.getFuzzyRuleSet().setVariable("current_speed", currentSpeed);
        fis.getFuzzyRuleSet().setVariable("distance_from_vehicle", distanceFromVehicle);
        fis.getFuzzyRuleSet().setVariable("slipperiness", slipperiness);
        fis.getFuzzyRuleSet().evaluate();
        return fis.getFuzzyRuleSet().getVariable("speed_change").defuzzify();
    }
}
