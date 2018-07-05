package imagetool.lhj.com.selector.adapter;


import imagetool.lhj.com.selector.bean.Image;

/**
 * Created by heyangya on 16-11-21.
 */

public interface ImageAction {
    void onActionSelect(Image image);

    void onActionCapture();
}
