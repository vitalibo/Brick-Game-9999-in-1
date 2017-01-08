package com.github.vitalibo.brickgame.core;

import com.github.vitalibo.brickgame.core.ui.BrickGameFrame;
import com.github.vitalibo.brickgame.core.ui.BrickPanel;
import com.github.vitalibo.brickgame.core.ui.IconPanel;
import com.github.vitalibo.brickgame.game.Game;
import com.github.vitalibo.brickgame.game.Menu;
import lombok.experimental.Delegate;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class ControllerTest {

    @Mock
    private BrickGameFrame mockFrame;
    @Mock
    private IconPanel mockPause;
    @Mock
    private IconPanel mockSound;
    @Mock
    private BrickPanel mockBoard;
    @Mock
    private BrickPanel mockPreview;
    @Mock
    private KeyEvent mockKeyEvent;
    @Spy
    private Kernel spyKernel;

    private Controller spyController;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockFrame.getPause()).thenReturn(mockPause);
        Mockito.when(mockFrame.getSound()).thenReturn(mockSound);
        Mockito.when(mockFrame.getBoard()).thenReturn(mockBoard);
        Mockito.when(mockFrame.getPreview()).thenReturn(mockPreview);
        spyController = Mockito.spy(new Controller(mockFrame, spyKernel));
        spyController.init(EmbeddedGame.class);
    }

    @DataProvider
    public Object[][] actions() {
        return new Object[][]{
            {KeyEvent.VK_UP, action(Controller::doUp)},
            {KeyEvent.VK_DOWN, action(Controller::doDown)},
            {KeyEvent.VK_LEFT, action(Controller::doLeft)},
            {KeyEvent.VK_RIGHT, action(Controller::doRight)},
            {KeyEvent.VK_SPACE, action(Controller::doRotate)}
        };
    }

    @Test(dataProvider = "actions")
    public void testDelegateKeyPressed(int keyEvent, Consumer<Controller> consumer) {
        Mockito.when(mockKeyEvent.getKeyCode()).thenReturn(keyEvent);

        spyController.keyPressed(mockKeyEvent);

        consumer.accept(Mockito.verify(spyController));
    }

    @Test(dataProvider = "actions")
    public void testNotDelegateKeyPressed(int keyEvent, Consumer<Controller> consumer) {
        Mockito.when(mockPause.get()).thenReturn(true);
        Mockito.when(mockKeyEvent.getKeyCode()).thenReturn(keyEvent);

        spyController.keyPressed(mockKeyEvent);

        consumer.accept(Mockito.verify(spyController, Mockito.never()));
    }

    @Test
    public void testChangeSound() {
        Mockito.when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_S);

        spyController.keyPressed(mockKeyEvent);

        Mockito.verify(mockSound).change();
    }

    @Test
    public void testChangePause() {
        Mockito.when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_P);
        Kernel.Job job = spyKernel.job(this, 100, o -> {
        });

        spyController.keyPressed(mockKeyEvent);

        boolean state = Mockito.verify(mockPause).change();
        Assert.assertEquals(job.isPause(), state);
    }

    @Test
    public void testReset() {
        Mockito.when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_R);
        Kernel.Job job = spyKernel.job(this, 100, o -> {
        });

        spyController.keyPressed(mockKeyEvent);

        Mockito.verify(spyController).init(Menu.class);
        Mockito.verify(spyController).unlock();
        Assert.assertTrue(job.isKilled());
    }

    private static Consumer<Controller> action(Consumer<Controller> action) {
        return action;
    }

    public static class EmbeddedGame extends Game {

        @Mock
        @Delegate
        private Game game;

        public EmbeddedGame(Context context) {
            super(context);
            MockitoAnnotations.initMocks(this);
        }

    }

}