package PictureFilter;
import java.awt.*;
/**
 * Created by Magda on 04.11.2016.
 */
public class FilterWrapper {
    private final PictureFilter filter;
    private final FilterType type;

    FilterWrapper(PictureFilter filter, FilterType type) {
        this.filter = filter;
        this.type = type;
    }

    public Color filter (int i, int j)  {
        return filter.filter(i, j);
    }

    public FilterType getType() {
        return type;
    }

}
