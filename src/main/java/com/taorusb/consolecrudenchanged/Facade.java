package com.taorusb.consolecrudenchanged;

import com.taorusb.consolecrudenchanged.commandhandler.commands.*;
import com.taorusb.consolecrudenchanged.controller.PostController;
import com.taorusb.consolecrudenchanged.controller.RegionController;
import com.taorusb.consolecrudenchanged.controller.WriterController;
import com.taorusb.consolecrudenchanged.repository.dataio.*;
import com.taorusb.consolecrudenchanged.view.ShowPost;
import com.taorusb.consolecrudenchanged.view.ShowRegion;
import com.taorusb.consolecrudenchanged.view.ShowWriter;

public class Facade {

    private QueryManipulationCommandController qmcc;
    private DeleteDirectionController ddc;
    private DeleteEndRequestReceiverController derrc;
    private InsertDirectionController idc;
    private InsertEndRequestReceiverController ierrc;
    private SelectDirectionController sdc;
    private SelectEndRequestReceiverController serrc;
    private UpdateDirectionController udc;
    private UpdateEndRequestReceiverController uerrc;

    private JsonWriterRepositoryImpl writerRepository;
    private JsonPostRepositoryImpl postRepository;
    private JsonRegionRepositoryImpl regionRepository;

    private Mediator mediator;
    private JsonDataIOImpl jsonDataIO;

    private WriterController writerController;
    private PostController postController;
    private RegionController regionController;

    private ShowWriter showWriter;
    private ShowPost showPost;
    private ShowRegion showRegion;

    private static Facade instance;

    private Facade() {
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void assembleApplication() {
        assembleRepo();
    }

    public void startApp(String query) {
        String[] arr = query.strip().split(" +");
        qmcc.handle(arr);
    }

    private void assembleRepo() {

        writerRepository = new JsonWriterRepositoryImpl();
        postRepository = new JsonPostRepositoryImpl();
        regionRepository = new JsonRegionRepositoryImpl();

        mediator = new Mediator();
        jsonDataIO = new JsonDataIOImpl();

        writerRepository.addLoader(postRepository);
        writerRepository.addLoader(regionRepository);
        writerRepository.setIo(mediator);

        postRepository.setIo(mediator);
        regionRepository.setIo(mediator);

        mediator.setDataIO(jsonDataIO);
        Mediator.addRepository("writerRepository", writerRepository);
        Mediator.addRepository("postRepository", postRepository);
        Mediator.addRepository("regionRepository", regionRepository);

        jsonDataIO.setGson(mediator.getGson());

        assembleControllers();
    }

    private void assembleControllers() {

        writerController = new WriterController();
        postController = new PostController();
        regionController = new RegionController();

        writerController.setWriterRepository(writerRepository);
        postController.setPostRepository(postRepository);
        postController.setWriterRepository(writerRepository);
        regionController.setRegionRepository(regionRepository);
        regionController.setWriterRepository(writerRepository);

        assembleView();
    }

    private void assembleView() {
        showWriter = new ShowWriter(writerController);
        showPost = new ShowPost(postController);
        showRegion = new ShowRegion(regionController);
        assembleChain();
    }

    private void assembleChain() {

        qmcc = new QueryManipulationCommandController();
        ddc = new DeleteDirectionController();
        derrc = new DeleteEndRequestReceiverController();
        idc = new InsertDirectionController();
        ierrc = new InsertEndRequestReceiverController();
        sdc = new SelectDirectionController();
        serrc = new SelectEndRequestReceiverController();
        udc = new UpdateDirectionController();
        uerrc = new UpdateEndRequestReceiverController();

        qmcc.setUpdateDirectionController(udc);
        qmcc.setInsertDirectionController(idc);
        qmcc.setDeleteDirectionController(ddc);
        qmcc.setSelectDirectionController(sdc);

        ddc.setEndRequestReceiverController(derrc);

        derrc.setShowWriter(showWriter);
        derrc.setShowRegion(showRegion);
        derrc.setShowPost(showPost);

        idc.setInsertEndRequestReceiverController(ierrc);

        ierrc.setShowWriter(showWriter);
        ierrc.setShowRegion(showRegion);
        ierrc.setShowPost(showPost);

        sdc.setSelectEndRequestReceiverController(serrc);

        serrc.setShowWriter(showWriter);
        serrc.setShowRegion(showRegion);
        serrc.setShowPost(showPost);

        udc.setEndRequestReceiverController(uerrc);

        uerrc.setShowWriter(showWriter);
        uerrc.setShowRegion(showRegion);
        uerrc.setShowPost(showPost);
    }
}
