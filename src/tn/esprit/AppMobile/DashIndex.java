/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.AppMobile;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
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
import tn.esprit.AppMobile.Services.ReclamationService;

/**
 *
 * @author Mega-pc
 */
public class DashIndex extends SideMenuBaseForm {

    ReclamationService rs = ReclamationService.getInstance();


    public DashIndex(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("logo.png");
        Image navimg = res.getImage("home_2.jpg");

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label("12", "CenterTitle"),
                new Label("Nombre Totale Des Etoiles", "CenterSubTitle")
        );
        // remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("pourcentage Des Etoiles ", "CenterSubTitle")
        );
        //  completedTasks.setUIID("CompletedTasks");

        Style titleCmpStyle = new Style();
        titleCmpStyle.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED);
        titleCmpStyle.setBgImage(navimg);

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label("Depanini", "Title"),
                                new Label("bienvenue", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePic),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD_TASK);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        // fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
        titleCmp.setUnselectedStyle(titleCmpStyle);
        titleCmp.setSelectedStyle(titleCmpStyle);
        add(new Label("Today", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        setupSideMenu(res);

        int nballReclamation = 0;
        int nbreclamationResolut = 0;
        int nbreclamationOuvert = 0;

        try {
            nbreclamationResolut = rs.getcountrecresolu();
            nbreclamationOuvert = rs.getcountrecouvert();
            nballReclamation = rs.getcountrec();
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }

        int nbreclamationEnAttent = nballReclamation - (nbreclamationResolut + nbreclamationOuvert);
        //------------
        double precResolu = (nbreclamationResolut * 100) / nballReclamation;
        double precOuvert = (nbreclamationOuvert * 100) / nballReclamation;
        double precEnAttent = (nbreclamationEnAttent * 100) / nballReclamation;

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
        PieChart chart = new PieChart(buildDataset("title", Math.round(precResolu), Math.round(precOuvert), Math.round(precEnAttent)), renderer);

        // n7oto chart fi component
        ChartComponent c = new ChartComponent(chart);

        String[] messages = {
            "Statistique Pourcentage des reclamation"
        };

        SpanLabel message = new SpanLabel(messages[0], "WelcomeMessage");

        Container cnt = BorderLayout.center(message);
        cnt.setUIID("Container");
        add(cnt);
        add(c);

    }

    private CategorySeries buildDataset(String title, double precResolu, double precOuvert, double precEnAttent) {

        CategorySeries series = new CategorySeries(title);

        series.add("Resolu", precResolu);
        series.add("Ouvert", precOuvert);
        series.add("En attent", precEnAttent);

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

    private void addButtonBottom(Image arrowDown, String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(), first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
