package com.kodnest.tunehub.service;

import java.util.List;

import com.kodnest.tunehub.entity.Playlist;

public interface PlaylistService {

	void addplaylist(Playlist playlist);

	List<Playlist> fetchAllPlaylists();

}
