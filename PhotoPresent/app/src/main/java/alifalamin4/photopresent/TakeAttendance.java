package alifalamin4.photopresent;

/**
 * Created by alifa on 03-Oct-16.
 */
public class TakeAttendance {
    private Integer reg;
    private boolean present;

    public TakeAttendance(Integer i){
        this.reg=i;
    }

    public TakeAttendance(Integer i,boolean present) {
        this.reg=i;
        this.present=present;
    }


    public Integer getReg() {
        return reg;
    }

    public void setReg(Integer reg) {
        this.reg = reg;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return reg+"";
    }
}
