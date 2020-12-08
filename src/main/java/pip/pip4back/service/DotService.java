package pip.pip4back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pip.pip4back.dto.DotDto;
import pip.pip4back.model.Dot;
import pip.pip4back.model.User;
import pip.pip4back.repository.DotRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class DotService {

    private AuthService authService;
    private AreaService areaService;
    private DotRepository dotRepository;

    @Autowired
    public DotService(AuthService authService, AreaService areaService, DotRepository dotRepository) {
        this.authService = authService;
        this.areaService = areaService;
        this.dotRepository = dotRepository;
    }

    public Dot addDot(DotDto dotDto) {
        Dot dot = new Dot();
        User user = authService.getCurrentUser();
        dot.setUserId(user);
        dot.setX(dotDto.getX());
        dot.setY(dotDto.getY());
        dot.setR(dotDto.getR());
        dot.setResult(areaService.isDotInside(dotDto));
        dot.setTime(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        dotRepository.save(dot);
        return dot;
    }

    public List<Dot> showAllDots() {
        User user = authService.getCurrentUser();
        return dotRepository.findAllByUserId(user);
    }


}
