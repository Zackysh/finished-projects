import axios from "axios";
import React, { useEffect, useState } from "react";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import "./app.css";
import Shows from "./components/Shows";
import "./components/UI/SearchBar/style.css";
import SearchTabs from "./components/UI/SearchTabs/SearchTabs";
import favsService from "./services/favsService";

const App = () => {
  const [shows, setShows] = useState([]); // show list
  const [filter, setFilter] = useState(""); // filter text
  const [favs, setFavs] = useState([]); // fav list
  const [fav, setFav] = useState(false); // when fav tab is active or not
  // fetch shows with axios at first
  useEffect(() => {
    favsService.downloadShows(setShows);
  }, []);
  // fetch favorites with axios at second
  useEffect(() => {
    favsService.downloadFavs(setFavs);
  }, []);
  
  return ( // render application
    <div className="application">
      <SearchTabs
        setFav={setFav}
        setFavs={setFavs}
        uploadFavs={favsService.uploadFavs}
        importFavs={favsService.importFavs}
        downloadJSONFavs={favsService.downloadJSONFavs}
        fav={fav}
        favs={favs}
        filter={filter}
        setFilter={setFilter}
      />
      <footer className="text-center">
        <Container>
          <Row>
            <Col>
              <Shows
                clickOnFav={favsService.clickOnFav}
                setFavs={setFavs}
                fav={fav}
                favs={favs}
                shows={
                  fav
                    ? favsService.showShows(favs, filter)
                    : favsService.showShows(shows, filter)
                }
                existOnFavs={favsService.existOnFavs}
              />
            </Col>
          </Row>
        </Container>
      </footer>
    </div>
  );
};

export default App;
