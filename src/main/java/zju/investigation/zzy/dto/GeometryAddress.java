package zju.investigation.zzy.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GeometryAddress {
    String type = "FeatureCollection";
    String crs = "{ \"type\": \"name\", \"properties\": { \"name\": \"urn:ogc:def:crs:OGC:1.3:CRS84\" } }";
    ArrayList<Features> features;
}
