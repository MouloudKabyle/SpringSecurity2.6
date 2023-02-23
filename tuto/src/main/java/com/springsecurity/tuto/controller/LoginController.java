package com.springsecurity.tuto.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

	private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

	public LoginController(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
		this.auth2AuthorizedClientService = auth2AuthorizedClientService;
	}

	@RolesAllowed("USER")
	@GetMapping("/**")
	public String getUser() {
		return "Hello i am User";
	}

	@RolesAllowed({ "USER", "ADMIN" })
	@GetMapping("/admin")
	public String getAdmin() {

		return "Hello i am Admin";
	}
 
	@RequestMapping("/*")
	public String getUserInfo(Principal user) {
		StringBuffer userInfo = new StringBuffer();

		if (user instanceof UsernamePasswordAuthenticationToken) {
			userInfo.append(getUsernamePasswordLoginInfo(user));
		} else {
			if (user instanceof OAuth2AuthenticationToken) {
				userInfo.append(getOauth2loginInfo(user));
			}
		}

		return userInfo.toString();
	}

	private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
		StringBuffer userNameInfo = new StringBuffer();
		UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
		if (token.isAuthenticated()) {
			User u = (User) token.getPrincipal();
			userNameInfo.append("Hello, " + u.getUsername());
		} else {
			userNameInfo.append("Not Found");
		}
		return userNameInfo;
	}

	private StringBuffer getOauth2loginInfo(Principal user) {
		StringBuffer protectedInfo = new StringBuffer();
		OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) user;
		OAuth2AuthorizedClient authorizedClient = this.auth2AuthorizedClientService.loadAuthorizedClient(
				authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
		if (authenticationToken.isAuthenticated()) {
			Map<String, Object> userAttributes = ((DefaultOAuth2User) authenticationToken.getPrincipal())
					.getAttributes();
			String userToken = authorizedClient.getAccessToken().getTokenValue();
			protectedInfo.append("my name is : " + userAttributes.get("name") + "<br><br>");
			protectedInfo.append("my email is : " + userAttributes.get("email") + "<br><br>");
			protectedInfo.append("my token is : " + userToken + "<br><br>");

		} else {
			protectedInfo.append("Not Found");
		}

		return protectedInfo;
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
	public List<MySong> getMyFavSongsRank(@PathVariable(value = "sort") String sortType) {

		List<MySong> sortedSongs = mySongsService.sortedSongs(sortType);

		return sortedSongs;
	}

}
