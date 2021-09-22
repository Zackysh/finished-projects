import axios from "axios";

const favsService = {};

favsService.downloadShows = (setShows) => {
  axios
    .get("http://localhost:8081/api/shows")
    .then((response) => {
      const data = JSON.parse(response.data);
      if (data) setShows(data);
    })
    .catch((error) => {
      console.log(error);
      throw error;
    });
};

/**
 * This method returns filtered shows.
 *
 * @param {[shows]} shows Shows to filter
 * @param {string} filter Filter to be applied
 * @returns {[shows]} filtered shows
 */
favsService.showShows = (shows, filter) =>
  shows.filter((show) =>
    JSON.stringify(show).toLowerCase().includes(filter.toLowerCase())
  );

/**
 * This method add a show to favs, then set a new
 * state for favs.
 *
 * @param {function} setFavs state setter for favs
 * @param {[show]} favs current state of favs
 * @param {show} show new show to add
 */
favsService.addFav = (setFavs, favs, show) => {
  setFavs(favs.concat(show));
  window.alert(`"${show.title}" has been added to your favorites list!`);
};

/**
 * This method removes a show from favs, then set a new
 * state for favs.
 *
 * @param {function} setFavs state setter for favs
 * @param {[show]} favs current state of favs
 * @param {show} show show to remove
 */
favsService.removeFav = (setFavs, favs, show) => {
  if (
    window.confirm(
      `You are about to delete "${show.title}" from favorites, continue?`
    )
  )
    setFavs(favs.filter((fav) => fav.show_id !== show.show_id));
};

/**
 * This method checks if given show exists on favs.
 *
 * @param {[show]} favs current state of favs
 * @param {show} show subject
 * @returns true if exists on favs
 * @returns false if doesn't exists on favs
 */
favsService.existOnFavs = (favs, show) => {
  return favs.filter((fav) => show.show_id == fav.show_id).length > 0;
};

/**
 * This method handle full process since fav button
 * is clicked to the show is added / removed from favs.
 *
 * @param {function} setFavs state setter for favs
 * @param {[]} favs current state of favs
 * @param {show} show show to add / remove
 */
favsService.clickOnFav = (setFavs, favs, show) => {
  if (favsService.existOnFavs(favs, show))
    favsService.removeFav(setFavs, favs, show);
  else favsService.addFav(setFavs, favs, show);
};

/**
 * This method is called once at first, it loads
 * favorites from API.
 *
 * @param {function} setFavs state setter for favs
 */
favsService.downloadFavs = (setFavs) => {
  axios
    .get("http://localhost:8081/api/favorites")
    .then((response) => {
      const data = JSON.parse(response.data);
      if (data) setFavs(data);
      window.alert("Your favorites has been loaded correctly! :)");
    })
    .catch((error) => {
      console.log(error);
      window.alert(
        "Your favorites couldn't be downloaded, sorry :(\n" +
          "You can still continue using the application and try to upload new favorites."
      );
    });
};

/**
 * This method is called on click event. It's intended
 * to update API favorites with new client side favorites.
 * It performs a simple POST request.
 *
 * @param {[show]} newFavs favs to upload (favs state)
 */
favsService.uploadFavs = (newFavs) => {
  newFavs = JSON.stringify(newFavs);
  axios
    .post(`http://localhost:8081/api/favorites`, newFavs, {
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      window.alert("Your favorites has been uploaded correctly! :)");
    })
    .catch((error) => {
      window.alert("Your favorites couldn't be uploaded, sorry :(");
    });
};

/**
 * This method trigger a download of a JSON file with
 * favs current state.
 *
 * @param {[show]} favs favs current sate
 */
favsService.downloadJSONFavs = (favs) => {
  const url = window.URL.createObjectURL(new Blob([JSON.stringify(favs)]));
  const link = document.createElement("a");
  link.href = url;
  link.setAttribute("download", "favorites.json"); //or any other extension
  document.body.appendChild(link);
  link.click();
};

/**
 * This method handle favorites import logic.
 * It checks JSON shows integrity, looks for duplicates and
 * concat existing with imported favorites.
 *
 * @param {[shows]} newFavs imported favorites
 * @param {[shows]} favs favs current state
 * @param {function} setFavs state setter for favs
 */
favsService.importFavs = (newFavs, favs, setFavs) => {
  let toRemove = [];

  // check JSON shows integrity and memorize duplicated favs
  favs.forEach((fav) => {
    newFavs.forEach((newFav) => {
      if (!newFav.show_id) {
        window.confirm(
          "Your JSON doesn't contain Shows! Try to export > re-import shows to test this feature.\n" +
            "You can also follow exported shows pattern to create your owns."
        );
        throw new Error("Failed to import new favorites");
      }
      // memorize duplicates
      if (fav.show_id == newFav.show_id) toRemove.push(newFav.show_id);
    });
  });
  // remove duplicates
  toRemove.forEach((id) => {
    newFavs = newFavs.filter((newFav) => newFav.show_id != id);
  });
  // concat current favs with imported ones
  setFavs(favs.concat(newFavs));
  window.alert("Your favorites has been imported correctly! :)");
};

export default favsService;
