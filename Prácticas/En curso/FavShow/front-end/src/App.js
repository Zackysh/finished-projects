import React, { useState, useEffect } from 'react'
import axios from 'axios'

// Model
import Shows from './components/Shows'
import Show from './components/Show'

// UI
import SearchBar from './components/UI/SearchBar'
import FullWidthTabs from './components/UI/FullWidthTabs'

import Col from 'react-bootstrap/Col'
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'

// Styles
import './styles/app.css'
import './styles/seachBar.css'

const mock = require('./mock')

const App = () => {
  const [shows, setShows] = useState([])
  const [filter, setFilter] = useState('')
  const [favs, setFavs] = useState([])
  const [fav, setFav] = useState(false)
  useEffect(() => {
    axios
      .get('http://localhost:8081/api/shows')
      .then(response => {
        setShows(JSON.parse(response.data))
      }).catch(error => {
        console.log(error)
        throw error
      })
      /* setShows([{"show_id":"s2","type":"Movie","title":"7:19","director":"Jorge Michel Grau","cast":"\"Demián Bichir, Héctor Bonilla, Oscar Serrano, Azalia Ortiz, Octavio Michel, Carmen Beato\"","country":"Mexico","date_added":"\"December 23, 2016\"","release_year":"2016","rating":"TV-MA","duration":"93 min","listed_in":"\"Dramas, International Movies\"","description":"\"After a devastating earthquake hits Mexico City, trapped survivors from all walks of life wait to be rescued while trying desperately to stay alive."},{"show_id":"s3","type":"Movie","title":"23:59","director":"Gilbert Chan","cast":"\"Tedd Chan, Stella Chung, Henley Hii, Lawrence Koh, Tommy Kuan, Josh Lai, Mark Lee, Susan Leong, Benjamin Lim\"","country":"Singapore","date_added":"\"December 20, 2018\"","release_year":"2011","rating":"R","duration":"78 min","listed_in":"\"Horror Movies, International Movies\"","description":"\"When an army recruit is found dead, his fellow soldiers are forced to confront a terrifying secret that's haunting their jungle island training camp."},{"show_id":"s4","type":"Movie","title":"9","director":"Shane Acker","cast":"\"Elijah Wood, John C. Reilly, Jennifer Connelly, Christopher Plummer, Crispin Glover, Martin Landau, Fred Tatasciore, Alan Oppenheimer, Tom Kane\"","country":"United States","date_added":"\"November 16, 2017\"","release_year":"2009","rating":"PG-13","duration":"80 min","listed_in":"\"Action & Adventure, Independent Movies, Sci-Fi & Fantasy\"","description":"\"In a postapocalyptic world, rag-doll robots hide in fear from dangerous machines out to exterminate them, until a brave newcomer joins the group."},{"show_id":"s5","type":"Movie","title":"21","director":"Robert Luketic","cast":"\"Jim Sturgess, Kevin Spacey, Kate Bosworth, Aaron Yoo, Liza Lapira, Jacob Pitts, Laurence Fishburne, Jack McGee, Josh Gad, Sam Golzari, Helen Carey, Jack Gilpin\"","country":"United States","date_added":"\"January 1, 2020\"","release_year":"2008","rating":"PG-13","duration":"123 min","listed_in":"Dramas","description":"A brilliant group of students become card-counting experts with the intent of swindling millions out of Las Vegas casinos by playing blackjack"},{"show_id":"s6","type":"TV Show","title":"46","director":"Serdar Akar","cast":"\"Erdal Beşikçioğlu, Yasemin Allen, Melis Birkan, Saygın Soysal, Berkan Şal, Metin Belgin, Ayça Eren, Selin Uludoğan, Özay Fecht, Suna Yıldızoğlu\"","country":"Turkey","date_added":"\"July 1, 2017\"","release_year":"2016","rating":"TV-MA","duration":"1 Season","listed_in":"\"International TV Shows, TV Dramas, TV Mysteries\"","description":"\"A genetics professor experiments with a treatment for his comatose sister that blends medical and shamanic cures, but unlocks a shocking side effect."},{"show_id":"s7","type":"Movie","title":"122","director":"Yasir Al Yasiri","cast":"\"Amina Khalil, Ahmed Dawood, Tarek Lotfy, Ahmed El Fishawy, Mahmoud Hijazi, Jihane Khalil, Asmaa Galal, Tara Emad\"","country":"Egypt","date_added":"\"June 1, 2020\"","release_year":"2019","rating":"TV-MA","duration":"95 min","listed_in":"\"Horror Movies, International Movies\"","description":"\"After an awful accident, a couple admitted to a grisly hospital are separated and must find each other to escape — before death finds them."},{"show_id":"s8","type":"Movie","title":"187","director":"Kevin Reynolds","cast":"\"Samuel L. Jackson, John Heard, Kelly Rowan, Clifton Collins Jr., Tony Plana\"","country":"United States","date_added":"\"November 1, 2019\"","release_year":"1997","rating":"R","duration":"119 min","listed_in":"Dramas","description":"\"After one of his high school students attacks him, dedicated teacher Trevor Garfield grows weary of the gang warfare in the New York City school system and moves to California to teach there, thinking it must be a less hostile environment."},{"show_id":"s9","type":"Movie","title":"706","director":"Shravan Kumar","cast":"\"Divya Dutta, Atul Kulkarni, Mohan Agashe, Anupam Shyam, Raayo S. Bakhirta, Yashvit Sancheti, Greeva Kansara, Archan Trivedi, Rajiv Pathak\"","country":"India","date_added":"\"April 1, 2019\"","release_year":"2019","rating":"TV-14","duration":"118 min","listed_in":"\"Horror Movies, International Movies\"","description":"\"When a doctor goes missing, his psychiatrist wife treats the bizarre medical condition of a psychic patient, who knows much more than he's leading on."},{"show_id":"s10","type":"Movie","title":"1920","director":"Vikram Bhatt","cast":"\"Rajneesh Duggal, Adah Sharma, Indraneil Sengupta, Anjori Alagh, Rajendranath Zutshi, Vipin Sharma, Amin Hajee, Shri Vallabh Vyas\"","country":"India","date_added":"\"December 15, 2017\"","release_year":"2008","rating":"TV-MA","duration":"143 min","listed_in":"\"Horror Movies, International Movies, Thrillers\"","description":"An architect and his wife move into a castle that is slated to become a luxury hotel. But something inside is determined to stop the renovation"},{"show_id":"s11","type":"Movie","title":"1922","director":"Zak Hilditch","cast":"\"Thomas Jane, Molly Parker, Dylan Schmid, Kaitlyn Bernard, Bob Frazer, Brian d'Arcy James, Neal McDonough\"","country":"United States","date_added":"\"October 20, 2017\"","release_year":"2017","rating":"TV-MA","duration":"103 min","listed_in":"\"Dramas, Thrillers\"","description":"\"A farmer pens a confession admitting to his wife's murder, but her death is just the beginning of a macabre tale. Based on Stephen King's novella."},{"show_id":"s12","type":"TV Show","title":"1983","director":"","cast":"\"Robert Więckiewicz, Maciej Musiał, Michalina Olszańska, Andrzej Chyra, Clive Russell, Zofia Wichłacz, Edyta Olszówka, Mateusz Kościukiewicz, Ewa Błaszczyk, Vu Le Hong, Tomasz Włosok, Krzysztof Wach\"","country":"\"Poland, United States\"","date_added":"\"November 30, 2018\"","release_year":"2018","rating":"TV-MA","duration":"1 Season","listed_in":"\"Crime TV Shows, International TV Shows, TV Dramas\"","description":"\"In this dark alt-history thriller, a naïve law student and a world-weary detective uncover a conspiracy that has tyrannized Poland for decades."}]) */
  }, [])

  const checkDuplicate = (shows) => {
    let seen = new Set();
    var hasDuplicates = shows.some(function (currentObject) {
      return seen.size === seen.add(currentObject.show_id).size;
    });
  }

  const showShows = () => {
    checkDuplicate(shows)
    return shows.filter(show =>
      JSON.stringify(show).toLowerCase().includes(filter.toLowerCase()))
  }

  const showFavs = () => {
    return favs.filter(show =>
      JSON.stringify(show).toLowerCase().includes(filter.toLowerCase()))
  }

  const addRemoveFav = (show) => {
    if (favs.length != 0) {
      const result = favs.filter(fav => show.show_id == fav.show_id)
      if (result.length < 1) {
        setFavs(favs.concat(show))
      } else {
        setFavs(favs.filter(fav => fav.show_id !== show.show_id))
      }
    } else {
      setFavs(favs.concat(show))
    }
  }

  const onDelete = (show) => {
    if (window.confirm('Deseas borrar pipo?'))
      addRemoveFav(show)
  }

  const showAllShows = (shows) => {
    const showList = () => shows.map(show =>
      <Show addRemoveFav={addRemoveFav} key={show.show_id} show={show} />
    )

    if (shows.length === 1) return <Show show={shows[0]} />
    if (shows.length < 10 && shows.length > 0) return <>{showList()}</>
    if (shows.length > 20) return <p>Too many matches, specify another filter</p>
    return <p>No matches</p>
  }

  return (
    <div className="application">
      <FullWidthTabs setFav={setFav} filter={filter} setFilter={setFilter} />
      <div id="mainBody" className="container text-center">
        <ul id="results">
        </ul>
      </div>
      <footer className="text-center">
        <Container>
          <Row>
            <Col>
              {
                fav === true
                  ? <Shows addRemoveFav={onDelete} fav={fav} shows={showFavs()} />
                  : <Shows addRemoveFav={onDelete} fav={fav} shows={showShows()} />
              }
            </Col>
          </Row>
        </Container>
      </footer>
    </div>
  )
}

export default App





/* {
  fav === true
    ? showAllShows(favs)
    : showAllShows(shows)
}
 */