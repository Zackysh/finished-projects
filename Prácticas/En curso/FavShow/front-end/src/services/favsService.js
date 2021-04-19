import axios from "axios";

const favsService = {};

favsService.showShows = (shows, filter) =>
  shows.filter((show) =>
    JSON.stringify(show).toLowerCase().includes(filter.toLowerCase())
  );

favsService.addFav = (setFavs, favs, show) => {
  setFavs(favs.concat(show));
  window.alert(`"${show.title}" has been added to your favorites list!`);
  console.log(favs);
};

favsService.removeFav = (setFavs, favs, show) => {
  if (
    window.confirm(
      `You are about to delete "${show.title}" from favorites, continue?`
    )
  )
    setFavs(favs.filter((fav) => fav.show_id !== show.show_id));
};

favsService.existOnFavs = (favs, show) => {
  return favs.filter((fav) => show.show_id == fav.show_id).length > 0;
};

favsService.clickOnFav = (setFavs, favs, show) => {
  console.log(favsService.existOnFavs(favs, show));
  if (favsService.existOnFavs(favs, show))
    favsService.removeFav(setFavs, favs, show);
  else favsService.addFav(setFavs, favs, show);
};

favsService.downloadFavs = (setFavs) => {
  axios
    .get("http://localhost:8081/api/favorites")
    .then((response) => {
      const data = JSON.parse(response.data);
      if (data) setFavs(data);
      window.alert("Your favorites has been downloaded correctly! :)");
    })
    .catch((error) => {
      console.log(error);
      window.alert(
        "Your favorites couldn't be downloaded, sorry :(\n" +
          "You can still continue using the application and try to upload new favorites."
      );
    });
};

favsService.uploadFavs = (newFavs) => {
  newFavs = JSON.stringify(newFavs);
  console.log(newFavs);
  axios
    .post(`http://localhost:8081/api/favorites`, newFavs, {
      headers: { "Content-Type": "application/json" },
    })
    .then((res) => {
      console.log(res);
      console.log(res.data);
      window.alert("Your favorites has been uploaded correctly! :)");
    })
    .catch((error) => {
      window.alert("Your favorites couldn't be uploaded, sorry :(");
    });
  favsService.restartAPI();
};

favsService.restartAPI = () => {
  let placeHolder = [];
  placeHolder = JSON.stringify(placeHolder)
  axios.post(`http://localhost:8081/api/restart`, placeHolder, {
    headers: { "Content-Type": "application/json" },
  })
  console.log("restarted")
};

export default favsService;
