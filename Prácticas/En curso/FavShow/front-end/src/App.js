import React, { useState, useEffect } from "react";
import axios from "axios";

// Model
import Shows from "./components/Shows";

// UI
import SearchTabs from "./components/UI/SearchTabs";

import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";

// Styles
import "./styles/app.css";
import "./styles/seachBar.css";

const App = () => {
  const [shows, setShows] = useState([]);
  const [filter, setFilter] = useState("");
  const [favs, setFavs] = useState([]);
  const [fav, setFav] = useState(false);
  useEffect(() => {
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
  }, []);

  const checkDuplicate = (shows) => {
    let seen = new Set();
    return shows.some(
      (currentObject) => seen.size === seen.add(currentObject.show_id).size
    );
  };

  const showShows = () =>
    shows.filter((show) =>
      JSON.stringify(show).toLowerCase().includes(filter.toLowerCase())
    );
  const showFavs = () =>
    favs.filter((show) =>
      JSON.stringify(show).toLowerCase().includes(filter.toLowerCase())
    );

  const clickOnFav = (show) => {
    console.log(existOnFavs(show));
    if (existOnFavs(show)) removeFav(show);
    else addFav(show);
  };

  const existOnFavs = (show) => {
    return favs.filter((fav) => show.show_id == fav.show_id).length > 0;
  };

  const addFav = (show) => {
    setFavs(favs.concat(show));
    window.alert(`"${show.title}" has been added to your favorites list!`);
  };

  const removeFav = (show) => {
    if (
      window.confirm(
        `You are about to delete "${show.title}" from favorites, continue?`
      )
    )
      setFavs(favs.filter((fav) => fav.show_id !== show.show_id));
  };

  return (
    <div className="application">
      <SearchTabs
        useState={useState}
        setFav={setFav}
        fav={fav}
        filter={filter}
        setFilter={setFilter}
      />
      <footer className="text-center">
        <Container>
          <Row>
            <Col>
              <Shows
                clickOnFav={clickOnFav}
                fav={fav}
                shows={fav ? showFavs() : showShows()}
                existOnFavs={existOnFavs}
              />
            </Col>
          </Row>
        </Container>
      </footer>
    </div>
  );
};

export default App;
