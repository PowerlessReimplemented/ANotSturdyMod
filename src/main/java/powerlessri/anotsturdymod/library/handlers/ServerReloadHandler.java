package powerlessri.anotsturdymod.library.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import powerlessri.anotsturdymod.library.IResourcesReloadable;


public class ServerReloadHandler extends CommonReloadHandler {

    private Set<IResourcesReloadable> all;
    private Set<IResourcesReloadable> json;

    private List<IResourcesReloadable> lang;
    private List<IResourcesReloadable> recipes;
    private List<IResourcesReloadable> cfg;

    public ServerReloadHandler() {
        super();

        this.all = new HashSet<IResourcesReloadable>();
        this.json = new HashSet<IResourcesReloadable>();

        this.lang = new ArrayList<IResourcesReloadable>();
        this.recipes = new ArrayList<IResourcesReloadable>();
        this.cfg = new ArrayList<IResourcesReloadable>();
    }

    @Override
    public void onReload() {
        this.all.forEach((r) -> {
            r.reload();
        });
    }

    @Override
    public void add(IResourcesReloadable target) {
        if(!this.all.contains(target))
            this.all.add(target);
    }

    @Override
    public void addLang(IResourcesReloadable target) {
        this.lang.add(target);

        if(!this.all.contains(target))
            this.add(target);
    }

    @Override
    public void addRecipe(IResourcesReloadable target) {
        this.recipes.add(target);

        if(!this.all.contains(target))
            this.add(target);
        if(!this.json.contains(target))
            this.json.add(target);
    }

    @Override
    public void addCfg(IResourcesReloadable target) {
        this.cfg.add(target);

        if(!this.all.contains(target))
            this.add(target);
        if(!this.json.contains(target))
            this.json.add(target);
    }

}
