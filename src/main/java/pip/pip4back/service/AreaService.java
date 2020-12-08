package pip.pip4back.service;

import org.springframework.stereotype.Service;
import pip.pip4back.dto.DotDto;

@Service
public class AreaService {

    public boolean isDotInside(DotDto dotDto) {

        double x = dotDto.getX();
        double y = dotDto.getY();
        double r = dotDto.getR();

        // Triangle
        /*
        x = 0 0 R/2
        y = 0 R/2 0

        (x1-x)*(y2-y1)-(x2-x1)*(y1-y)
        (x2-x)*(y3-y2)-(x3-x2)*(y2-y)
        (x3-x)*(y1-y3)-(x1-x3)*(y3-y)
        */

        double d1 = -x * (r/2);
        double d2 = -x * (-r/2) - (r/2) * ((r/2) - y);
        double d3 = (r/2) * (-y);

        boolean isInside;

        isInside =
                ((x > 0) && (x <= r) && (y < 0) && (y >= (-r / 2))) ||
                ((x < 0) && (y < 0) && ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r, 2))) ||
                ((x > 0) && (y > 0) && (d1 > 0) && (d2 > 0) && (d3 > 0)) ||
                ((x > 0) && (y > 0) && (d1 < 0) && (d2 < 0) && (d3 < 0)) ||
                ((x == 0) && (y >= (-r)) && (y <= r / 2)) ||
                ((y == 0) && (x >= -r) && (x <= r));

        return isInside;
    }

}
