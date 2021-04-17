import React from 'react'
import Show from './Show'
import { Accordion, Card, Button } from 'react-bootstrap'


const Shows = ({ shows, addRemoveFav }) => {

    console.log('ADD REMOVE FAV JODER');
    console.log(addRemoveFav);

    const showList = () => shows.map(show => 
        <Show addRemoveFav={addRemoveFav} key={show.show_id} show={show} />
    )

    if (shows.length === 1) return <Show addRemoveFav={addRemoveFav} show={shows[0]} />
    if (shows.length < 10 && shows.length > 0) return <>{showList()}</>
    if (shows.length > 20) return <p>Too many matches, specify another filter</p>
    return <p>No matches</p>
}

export default Shows