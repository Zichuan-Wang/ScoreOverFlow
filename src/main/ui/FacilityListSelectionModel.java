package ui;

import javax.swing.DefaultListSelectionModel;

public class FacilityListSelectionModel extends DefaultListSelectionModel{

	private static final long serialVersionUID = 1L;

	private int i0 = -1;
    private int i1 = -1;

    @Override
    public void setSelectionInterval(int index0, int index1) {
        if(i0 == index0 && i1 == index1){
            if(getValueIsAdjusting()){
                 setValueIsAdjusting(false);
                 setSelection(index0, index1);
            }
        }else{
            i0 = index0;
            i1 = index1;
            setValueIsAdjusting(false);
            setSelection(index0, index1);
        }
    }
    private void setSelection(int index0, int index1){
        if(super.isSelectedIndex(index0)) {
            super.removeSelectionInterval(index0, index1);
        }else {
            super.addSelectionInterval(index0, index1);
        }
    }
	
}
