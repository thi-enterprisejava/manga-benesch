package de.thi.manga.web.model;

import de.thi.manga.service.MangaService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ImageStreamer {

    @Inject
    private MangaService mangaService;

    public byte[] getImageByMangaId(Long id) {
        if (id == null) {
            return new byte[0];
        }
        System.out.println("Id: " + id);
        System.out.println("Found Manga: " + mangaService.findById(id));
        System.out.println("Found Manga image: " + mangaService.findById(id).getCover());
        return mangaService.findById(id).getCover();
    }

}
