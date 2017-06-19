package com.example.administrator.promptview.widget;


import com.example.administrator.promptview.widget.location.BottomCenterLocation;
import com.example.administrator.promptview.widget.location.ICalculateLocation;
import com.example.administrator.promptview.widget.location.TopCenterLocation;
import com.example.administrator.promptview.widget.location.TopLeftLocation;
import com.example.administrator.promptview.widget.location.TopRightLocation;

public enum Location {

    TOP_LEFT(1),
    TOP_CENTER(2),
    TOP_RIGHT(3),
    BOTTOM_LEFT(4),
    BOTTOM_CENTER(5),
    BOTTOM_RIGHT(6);

    ICalculateLocation calculateLocation;

    private Location(int type) {
        switch (type) {
            case 1:
                calculateLocation = new TopLeftLocation();
                break;
            case 2:
                calculateLocation = new TopCenterLocation();
                break;
            case 3:
                calculateLocation = new TopRightLocation();
                break;
            case 4:
                calculateLocation = new TopLeftLocation();
                break;
            case 5:
                calculateLocation = new BottomCenterLocation();
                break;
            case 6:
                calculateLocation = new TopLeftLocation();
                break;
        }
    }

}
