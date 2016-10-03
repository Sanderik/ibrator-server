<?php

namespace ApiBundle\Controller;

use ApiBundle\Entity\Device;
use FOS\RestBundle\Controller\Annotations\Post;
use FOS\RestBundle\Controller\Annotations\Put;
use FOS\RestBundle\Controller\Annotations\RequestParam;
use FOS\RestBundle\Controller\FOSRestController;
use FOS\RestBundle\Request\ParamFetcher;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\JsonResponse;


class DeviceController extends FOSRestController
{
    /**
     * Register a new device (ESP module).
     *
     * @Post("/device")
     * @RequestParam(name="hostname", description="The current IP of the ESP module")
     * @ApiDoc(section="Device")
     */
    public function registerDeviceAction(ParamFetcher $fetcher){
        $em = $this->getDoctrine()->getManager();
        $device   = new Device($fetcher->get('hostname'));

        $em->persist($device);
        $em->flush();

        return new JsonResponse(array(
            "id" => $device->getId(),
        ), 201);
    }

    /**
     * Update the hostname of a deivce (ESP module).
     *
     * @Put("/device/{id}")
     * @ParamConverter("id", class="ApiBundle:Device")
     * @RequestParam(name="hostname", description="The current IP of the ESP module")
     * @ApiDoc(section="Device")
     */
    public function updateDeviceAction(Device $device, ParamFetcher $fetcher){
        $em = $this->getDoctrine()->getManager();

        $device->setHostname($fetcher->get('hostname'));
        $em->flush();

        return new JsonResponse("", 204);
    }

}
