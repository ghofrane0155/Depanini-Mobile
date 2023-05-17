package tn.esprit.AppMobile.GUI.Events;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.UnsupportedEncodingException;
import java.util.List;
import tn.esprit.AppMobile.Services.EventsService;

import tn.esprit.AppMobile.Services.EventsStat;
import tn.esprit.AppMobile.SideMenuBaseForm;


public class EventsPieChart extends SideMenuBaseForm {
    EventsStat Es =new EventsStat();
    EventsService es =EventsService.getInstance();
    EventsStat St =EventsStat.getInstance();
     public EventsPieChart(Resources res)  {
        super(new BorderLayout());
        
             
        setupSideMenu(res);
        
        
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        
        
        Image navimg = res.getImage("event1 (3).jpg");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        int x = 0;
        try {
            x = St.totalEvents();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        int y = 0;
        try {
            y = St.totalTickets();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        
        Container remainingTasks = BoxLayout.encloseY(
                        new Label(""+x, "CenterTitle"),
                        new Label("Total Events ", "CenterSubTitle")
                );
        
        Container completedTasks = BoxLayout.encloseY(
                        new Label(""+y, "CenterTitle"),
                        new Label("completed tasks", "CenterSubTitle")
        );
        

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);
        
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label("Client Name", "SubTitle"),
                                    new Label("Your Events", "Title")
                                )
                            ),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.addActionListener(e -> {
            new CreateEvent(res).show();
        });
        
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);
        
        
                        
       
        
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        
       
        int event1 = 0;
        int event2 = 0;
        int event3 = 0;

        //return the three integers from Es.getPieChartData() and add them to the event 1 2 and 3
        
        List<Integer> pieChartData = null;
        try {
            pieChartData = Es.getPieChartData();
            
            if (pieChartData.size() >= 3) {
            event1 = pieChartData.get(0);
            event2 = pieChartData.get(1);
            event3 = pieChartData.get(2);
        }
            
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        

        int e =event1+event2+event3;
        //calculate the pourcentage of every event like this
 
        double event1p = (event1 * 100) / e;
        double event2p = (event2 * 100) / e;
        double event3p = (event3 * 100) / e;
        
        

        //colors set:
        int[] colors = new int[]{0xf4b342, 0x52b29a,0x4dc2ff};

        DefaultRenderer renderer = buildCatRendrer(colors);
        renderer.setLabelsColor(0x000000); // black color for labels.

        renderer.setZoomButtonsVisible(true);//zoom
        renderer.setLabelsTextSize(50);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(50);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setHighlighted(true);

        //CREATe the chart ...
        PieChart chart = new PieChart(buildDataset("title", Math.round(event1p), Math.round(event2p), Math.round(event3p)), renderer);

        // n7oto chart fi component
        ChartComponent c = new ChartComponent(chart);

        String[] messages = {
            "Statistique Pourcentage des evenements"
        };

        SpanLabel message = new SpanLabel(messages[0], "WelcomeMessage");

        Container cnt = BorderLayout.center(message);
        cnt.setUIID("Container");
        add(BorderLayout.CENTER, cnt);
        add(BorderLayout.SOUTH, c);
    }
    
    

    @Override
    protected void showOtherForm(Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private CategorySeries buildDataset(String title, double event11p, double event22p, double event33p) {

        CategorySeries series = new CategorySeries(title);

        series.add("Top1", event11p);
        series.add("Top2", event22p);
        series.add("Top3", event33p);

        return series;
    }

    public DefaultRenderer buildCatRendrer(int[] colors) {

        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(50);
        renderer.setLegendTextSize(50);
        renderer.setMargins(new int[]{20, 30, 15, 0});

        for (int color : colors) {
            SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();

            simpleSeriesRenderer.setColor(color);
            renderer.addSeriesRenderer(simpleSeriesRenderer);
        }
        return renderer;
    }

    

    
}