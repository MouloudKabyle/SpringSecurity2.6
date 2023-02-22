package com.springsecurity.tuto.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.tuto.model.MySong;
import com.springsecurity.tuto.service.BinarySearchService;
import com.springsecurity.tuto.service.MySongsService;

@RestController
public class LoginController {
	@Autowired
	BinarySearchService binarySearchService;
	@Autowired
	MySongsService mySongsService;

	@RolesAllowed("USER")
	@GetMapping("/*")
	public String getUser() {
		return "Hello i am User";
	}

	@RolesAllowed({ "USER", "ADMIN" })
	@GetMapping("/admin")
	public String getAdmin() {

		return "Hello i am Admin";
	}

	@GetMapping("/binayInt/{value}")
	public String BinarySearchInt(@PathVariable(value = "value") int valueToSearch) {
		// binar
		List<Integer> list = new ArrayList<>();
		list.add(10);
		list.add(8);
		list.add(14);
		list.add(30);
		list.add(0);

		int pos = binarySearchService.searchIntInList(list, valueToSearch);
		if (pos == -1) {
			return "NotFound";
		} else {
			return "found at position " + pos;
		}
	}

	@GetMapping("/binayStr/{value}")
	public String BinarySearchStr(@PathVariable(value = "value") String valueToSearch) {
		// binar
		List<String> list = new ArrayList<>();
		list.add("aerzfez");
		list.add("xfdfd");
		list.add("fdsfdf");
		list.add("ⵣ");
		list.add("gerggeg");
		list.add("0");

		int pos = binarySearchService.searchStringInList(list, valueToSearch);
		if (pos == -1) {
			return "NotFound";
		} else {
			return "found at position " + pos;
		}
	}

	@GetMapping("/CreatSongs")
	public String createSongs() {
		String str = mySongsService.createSongs();
		return str;

	}

	@GetMapping("/listenSong/{title}")
	public String hearSong(@PathVariable(value = "title") String title) {

		String strSong = mySongsService.getSong(title);
		if (strSong != null) {
			return strSong;
		} else {
			return "Song Not found";
		}
	}

	@GetMapping("/myFav")
	public List<MySong> getMyFavSongsRank() {
		List<MySong> favSongs = mySongsService.getSongsByRank();
		return favSongs;
	}

	@GetMapping("/sortSongs/{sort}")
	public List<MySong>getMyFavSongsRank(@PathVariable(value="sort") String sortType){
		 
			List<MySong> sortedSongs = mySongsService.sortedSongs(sortType);
		 
		 
		return sortedSongs;
	}

}
