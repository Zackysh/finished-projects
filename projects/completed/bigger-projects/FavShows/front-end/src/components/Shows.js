import React from "react";
import Show from "./Show";

const Shows = ({ shows, existOnFavs, setFavs, favs, clickOnFav }) => {
  if (shows.length > 20) return <p>Too many matches, specify another filter</p>;
  if (shows.length > 0)
    return shows.map((show) => (
      <Show
        key={show.show_id}
        show={show}
        existOnFavs={existOnFavs}
        setFavs={setFavs}
        favs={favs}
        clickOnFav={clickOnFav}
      />
    ));
  return <p>No matches</p>;
};

export default Shows;
