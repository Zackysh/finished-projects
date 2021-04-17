import React from 'react'
import Show from './Show'
import { Accordion, Card, Button } from 'react-bootstrap'


const Shows = ({ shows, existOnFavs, clickOnFav }) => {
    if (shows.length > 20)
        return <p>Too many matches, specify another filter</p>
    if (shows.length > 0)
        return shows.map(show => <Show key={show.show_id} show={show} existOnFavs={existOnFavs} clickOnFav={clickOnFav} />)
    return <p>No matches</p>
}

export default Shows