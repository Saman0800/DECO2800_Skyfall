package deco2800.skyfall.saving;

import deco2800.skyfall.worlds.generation.VoronoiEdge;

import java.util.List;

public class EdgeSaveInfo {

    // The ID of the first edge node of this edge
    private int nodeAID;

    // The ID of the second edge node of this edge
    private int nodeBID;

    // null / river / beach
    private String edgeType;

    // The coordinates of the two vertices of this edge
    private double ax;
    private double ay;
    private double bx;
    private double by;

    public EdgeSaveInfo (VoronoiEdge edge, List<VoronoiEdge> riverEdges, List<VoronoiEdge> beachEdges) {
        this.nodeAID = edge.getEdgeNodes().get(0).getSaveID();
        this.nodeBID = edge.getEdgeNodes().get(1).getSaveID();

        if (riverEdges.contains(edge)) {
            this.edgeType = "river";
        } else if (beachEdges.contains(edge)) {
            this.edgeType = "beach";
        } else {
            this.edgeType = "null";
        }

        this.ax = edge.getA()[0];
        this.ay = edge.getA()[1];
        this.bx = edge.getB()[0];
        this.by = edge.getB()[1];
    }

    public int getNodeAID() {
        return this.nodeAID;
    }

    public int getNodeBID() {
        return this.nodeBID;
    }

    public String getEdgeType() {
        return this.edgeType;
    }

    public double getAX() {
        return this.ax;
    }

    public double getAY() {
        return this.ay;
    }

    public double getBX() {
        return this.bx;
    }

    public double getBY() {
        return this.by;
    }
}
