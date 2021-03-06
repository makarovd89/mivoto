package com.herokuapp.mivoto.web.admin;

import com.herokuapp.mivoto.model.Restaurant;
import com.herokuapp.mivoto.service.RestaurantService;
import com.herokuapp.mivoto.to.RestaurantTo;

import com.herokuapp.mivoto.to.RestaurantWithMenuTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.herokuapp.mivoto.util.ValidationUtil.assureIdConsistent;
import static com.herokuapp.mivoto.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("update restaurant {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        RestaurantTo created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantTo> getRestaurants() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/menu/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RestaurantWithMenuTo> getRestaurantsWithMenu(@RequestParam(name = "date") LocalDate date) {
        log.info("get all restaurants for date {}", date);
        return restaurantService.getAllWithMenuByDate(date);
    }
}
