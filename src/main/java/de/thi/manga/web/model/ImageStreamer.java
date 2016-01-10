package de.thi.manga.web.model;

import de.thi.manga.domain.Manga;
import de.thi.manga.service.MangaService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ImageStreamer {

    @Inject
    private transient MangaService mangaService;

    public byte[] getCoverByMangaId(Long id) {
        if (id == null) {
            return new byte[0];
        }
        Manga manga = mangaService.findById(id);
        try {
            return manga.getCover();
        } catch (NullPointerException e) {
            //kein Cover
            return new byte[0];
        }
    }

}
