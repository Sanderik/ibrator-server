<?php

namespace ApiBundle\Controller;

use FOS\RestBundle\Controller\Annotations\Get;
use FOS\RestBundle\Controller\FOSRestController;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;
use Symfony\Component\HttpFoundation\JsonResponse;

class OTAUpdateController extends FOSRestController
{
    /**
     * Poll for updates
     *
     * @Get("/update")
     * @ApiDoc(section="OTA Update")
     */
    public function checkForUpdateAction(){
        // TODO : Call some kind of service that checks for updates.
        return new JsonResponse("");
    }
}
