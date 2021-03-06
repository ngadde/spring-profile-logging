/*
 * Copyright (C)  2018 Miroslav Wengner
 *                        http://www.wengnermiro.com/
 *
 *  This software is free:
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESSED OR
 *   IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *   OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *   IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *   NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *   THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *   Copyright (C) Miroslav Wengner, 2018
 */

package com.mirowengner.example.consumer.controller;

import com.mirowengner.example.consumer.model.VehicleModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * VehicleShopController
 *
 * @author Miroslav Wengner (@miragemiko)
 */
@RestController
@RequestMapping(value = "/shop")
public class VehicleShopController {

    private final AtomicInteger counter = new AtomicInteger();
    private final Map<Integer, VehicleModel> vehicles = new HashMap<>();

    @RequestMapping(value = "/models", method =
            RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Collection<VehicleModel> vehiclesGet() {
        return vehicles.values();
    }

    @RequestMapping(value = "/models/vehicle", method =
            RequestMethod.GET,
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public VehicleModel vehicleGet(@RequestParam(value = "id") Integer id) {
        return vehicles.get(id);
    }

    @RequestMapping(value = "/models/vehicle", method =
            RequestMethod.POST,
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public VehicleModel vehiclePost(@RequestBody VehicleModel vehicle) {
        final int nextId = counter.getAndIncrement();
        vehicle.setId(nextId);
        vehicles.put(nextId, vehicle);
        return vehicle;
    }

    @RequestMapping(value = "/models/vehicle", method =
            RequestMethod.PUT,
            produces = {"application/json"},
            consumes = {"application/json"})
    @ResponseBody
    public VehicleModel vehiclePut(@RequestBody VehicleModel vehicle) {
        vehicles.replace(vehicle.getId(), vehicle);
        return vehicle;
    }

}
