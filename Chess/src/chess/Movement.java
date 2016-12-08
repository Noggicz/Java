/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Dro0076
 */
public class Movement {

    private Model model;
    private Figures figure;
    private Free free;
    private boolean side = true;
    private boolean pawnTaking = false;
    private boolean pom;
    private boolean mahmadan;

    public Movement() {
        model = new Model();
        free = new Free();
    }

    public synchronized boolean MoveThatFigure(Figures.Color color, Figures.Type typ, Point pozice, Point direction, ArrayList<ModelObject> objekty) {
        ArrayList<ModelObject> objectsForMovement = new ArrayList<ModelObject>(objekty.size());
        for (Object obj : objekty) {
            objectsForMovement.add((ModelObject) obj);
        }

        Point pomPozice = new Point(pozice);
        Point pomDirxM = new Point(direction);
        Point pomDiryM = new Point(direction);
        Point pomDirxP = new Point(direction);
        Point pomDiryP = new Point(direction);
        Point BeforeLast = new Point();

        pomDirxM.x = pomDirxM.x - 1;
        pomDiryM.y = pomDiryM.y - 1;
        pomDirxP.x = pomDirxP.x + 1;
        pomDiryP.y = pomDiryP.y + 1;

        if (color == Figures.Color.White) {
            if (typ == Figures.Type.Pawn) {
                if ((direction.x == pozice.x && direction.y == pozice.y - 1)
                        && free.isFree(direction, objectsForMovement)) {
                    side = false;
                    return true;
                }
                if ((direction.x == pozice.x - 1 && direction.y == pozice.y - 1
                        || direction.x == pozice.x + 1 && direction.y == pozice.y - 1)
                        && !free.isFree(direction, objectsForMovement)) {
                    side = false;
                    this.pawnTaking = true;
                    return true;
                }
            }

            if (typ == Figures.Type.Rook) {
                if (pozice.equals(pomDirxM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDirxP)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryP)) {
                    side = false;
                    return true;
                } else {
                    do {
                        if ((direction.x == pomPozice.x && direction.y >= pomPozice.y)) {
                            pomPozice.y = pomPozice.y + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDiryM;
                        } else if (direction.x == pomPozice.x && direction.y <= pomPozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDiryP;
                        } else if (direction.x <= pomPozice.x && direction.y == pomPozice.y) {
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDirxP;
                        } else if (direction.x >= pomPozice.x && direction.y == pomPozice.y) {
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDirxM;
                        }
                        if (mahmadan == false) {
                            break;
                        }

                    } while ((pomPozice.x != BeforeLast.x) || (pomPozice.y != BeforeLast.y));
                    if (mahmadan == true) {
                        side = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            if (typ == Figures.Type.Knight) {
                if ((direction.x == pozice.x + 2 && direction.y == pozice.y - 1)
                        || (direction.x == pozice.x - 2 && direction.y == pozice.y - 1)
                        || (direction.x == pozice.x - 2 && direction.y == pozice.y + 1)
                        || (direction.x == pozice.x + 2 && direction.y == pozice.y + 1)
                        || (direction.x == pozice.x + 1 && direction.y == pozice.y + 2)
                        || (direction.x == pozice.x + 1 && direction.y == pozice.y - 2)
                        || (direction.x == pozice.x - 1 && direction.y == pozice.y - 2)
                        || (direction.x == pozice.x - 1 && direction.y == pozice.y + 2)) {
                    side = false;
                    return true;
                }
            }

            if (typ == Figures.Type.Bishop) {
                if (pozice.x == pomDirxM.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxP.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryM.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryP.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                } else {
                    do {
                        if (direction.x >= pozice.x && direction.y >= pozice.y) {
                            pomPozice.y = pomPozice.y + 1;
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxM.x;
                            BeforeLast.y = pomDiryM.y;
                        } else if (direction.x <= pozice.x && direction.y >= pozice.y) {
                            pomPozice.y = pomPozice.y + 1;
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxP.x;
                            BeforeLast.y = pomDiryM.y;
                        } else if (direction.x <= pozice.x && direction.y <= pozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxP.x;
                            BeforeLast.y = pomDiryP.y;
                        } else if (direction.x >= pozice.x && direction.y <= pozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxM.x;
                            BeforeLast.y = pomDiryP.y;
                        }
                        if (mahmadan == false) {
                            break;
                        }
                    } while ((pomPozice.x != BeforeLast.x) && (pomPozice.y != BeforeLast.y));
                    if ((Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y)) && mahmadan == true) {
                        side = false;
                        return true;
                    }
                }
            }

            if (typ == Figures.Type.Queen) {
                if (pozice.equals(pomDirxM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDirxP)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryP)) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxM.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxP.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryM.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryP.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                } else {
                    if (((direction.x >= pozice.x && direction.y >= pozice.y)
                            || (direction.x <= pozice.x && direction.y >= pozice.y)
                            || (direction.x <= pozice.x && direction.y <= pozice.y)
                            || (direction.x >= pozice.x && direction.y <= pozice.y))
                            && (Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y))) {
                        do {
                            if (direction.x >= pozice.x && direction.y >= pozice.y) {
                                pomPozice.y = pomPozice.y + 1;
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxM.x;
                                BeforeLast.y = pomDiryM.y;
                            } else if (direction.x <= pozice.x && direction.y >= pozice.y) {
                                pomPozice.y = pomPozice.y + 1;
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxP.x;
                                BeforeLast.y = pomDiryM.y;
                            } else if (direction.x <= pozice.x && direction.y <= pozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxP.x;
                                BeforeLast.y = pomDiryP.y;
                            } else if (direction.x >= pozice.x && direction.y <= pozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxM.x;
                                BeforeLast.y = pomDiryP.y;
                            }
                            if (mahmadan == false) {
                                break;
                            }
                        } while ((pomPozice.x != BeforeLast.x) && (pomPozice.y != BeforeLast.y));
                        if ((Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y)) && mahmadan == true) {
                            side = false;
                            return true;
                        }
                    }
                    if ((direction.x == pozice.x && direction.y >= pozice.y)
                            || (direction.x == pozice.x && direction.y <= pozice.y)
                            || (direction.x <= pozice.x && direction.y == pozice.y)
                            || (direction.x >= pozice.x && direction.y == pozice.y)) {
                        do {
                            if ((direction.x == pomPozice.x && direction.y >= pomPozice.y)) {
                                pomPozice.y = pomPozice.y + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDiryM;
                            } else if (direction.x == pomPozice.x && direction.y <= pomPozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDiryP;
                            } else if (direction.x <= pomPozice.x && direction.y == pomPozice.y) {
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDirxP;
                            } else if (direction.x >= pomPozice.x && direction.y == pomPozice.y) {
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDirxM;
                            }
                            if (mahmadan == false) {
                                break;
                            }

                        } while ((pomPozice.x != BeforeLast.x) || (pomPozice.y != BeforeLast.y));
                        if (mahmadan == true) {
                            side = false;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }

            if (typ == Figures.Type.King) {
                if (direction.x == pozice.x && direction.y == pozice.y - 1 || direction.x == pozice.x - 1 && direction.y == pozice.y - 1
                        || direction.x == pozice.x && direction.y == pozice.y + 1 || direction.x == pozice.x - 1 && direction.y == pozice.y + 1
                        || direction.x == pozice.x + 1 && direction.y == pozice.y || direction.x == pozice.x + 1 && direction.y == pozice.y - 1
                        || direction.x == pozice.x - 1 && direction.y == pozice.y || direction.x == pozice.x + 1 && direction.y == pozice.y + 1) {
                    side = false;
                    return true;
                }
            }

        } else if (color == Figures.Color.Black) {/* && side == false*/

            if (typ == Figures.Type.Pawn) {
                if ((direction.x == pozice.x && direction.y == pozice.y + 1)
                        && free.isFree(direction, objectsForMovement)) {
                    side = true;
                    return true;
                } 
                if ((direction.x == pozice.x - 1 && direction.y == pozice.y + 1
                        || direction.x == pozice.x + 1 && direction.y == pozice.y + 1)
                        && !free.isFree(direction, objectsForMovement)) {
                    side = true;
                    this.pawnTaking = true;
                    return true;
                }
            }

            if (typ == Figures.Type.Rook) {
                if (pozice.equals(pomDirxM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDirxP)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryP)) {
                    side = false;
                    return true;
                } else {
                    do {
                        if ((direction.x == pomPozice.x && direction.y >= pomPozice.y)) {
                            pomPozice.y = pomPozice.y + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDiryM;
                        } else if (direction.x == pomPozice.x && direction.y <= pomPozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDiryP;
                        } else if (direction.x <= pomPozice.x && direction.y == pomPozice.y) {
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDirxP;
                        } else if (direction.x >= pomPozice.x && direction.y == pomPozice.y) {
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast = pomDirxM;
                        }
                        if (mahmadan == false) {
                            break;
                        }

                    } while ((pomPozice.x != BeforeLast.x) || (pomPozice.y != BeforeLast.y));
                    if (mahmadan == true) {
                        side = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            if (typ == Figures.Type.Knight) {
                if ((direction.x == pozice.x + 2 && direction.y == pozice.y - 1)
                        || (direction.x == pozice.x - 2 && direction.y == pozice.y - 1)
                        || (direction.x == pozice.x - 2 && direction.y == pozice.y + 1)
                        || (direction.x == pozice.x + 2 && direction.y == pozice.y + 1)
                        || (direction.x == pozice.x + 1 && direction.y == pozice.y + 2)
                        || (direction.x == pozice.x + 1 && direction.y == pozice.y - 2)
                        || (direction.x == pozice.x - 1 && direction.y == pozice.y - 2)
                        || (direction.x == pozice.x - 1 && direction.y == pozice.y + 2)) {
                    side = true;
                    return true;
                }
            }

            if (typ == Figures.Type.Bishop) {
                if (pozice.x == pomDirxM.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxP.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryM.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryP.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                } else {
                    do {
                        if (direction.x >= pozice.x && direction.y >= pozice.y) {
                            pomPozice.y = pomPozice.y + 1;
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxM.x;
                            BeforeLast.y = pomDiryM.y;
                        } else if (direction.x <= pozice.x && direction.y >= pozice.y) {
                            pomPozice.y = pomPozice.y + 1;
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxP.x;
                            BeforeLast.y = pomDiryM.y;
                        } else if (direction.x <= pozice.x && direction.y <= pozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            pomPozice.x = pomPozice.x - 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxP.x;
                            BeforeLast.y = pomDiryP.y;
                        } else if (direction.x >= pozice.x && direction.y <= pozice.y) {
                            pomPozice.y = pomPozice.y - 1;
                            pomPozice.x = pomPozice.x + 1;
                            mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                            BeforeLast.x = pomDirxM.x;
                            BeforeLast.y = pomDiryP.y;
                        }
                        if (mahmadan == false) {
                            break;
                        }
                    } while ((pomPozice.x != BeforeLast.x) && (pomPozice.y != BeforeLast.y));
                    if ((Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y)) && mahmadan == true) {
                        side = false;
                        return true;
                    }
                }
            }

            if (typ == Figures.Type.Queen) {
                if (pozice.equals(pomDirxM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDirxP)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryM)) {
                    side = false;
                    return true;
                }
                if (pozice.equals(pomDiryP)) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxM.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDirxP.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryM.x && pozice.y == pomDiryP.y) {
                    side = false;
                    return true;
                }
                if (pozice.x == pomDiryP.x && pozice.y == pomDiryM.y) {
                    side = false;
                    return true;
                } else {
                    if (((direction.x >= pozice.x && direction.y >= pozice.y)
                            || (direction.x <= pozice.x && direction.y >= pozice.y)
                            || (direction.x <= pozice.x && direction.y <= pozice.y)
                            || (direction.x >= pozice.x && direction.y <= pozice.y))
                            && (Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y))) {
                        do {
                            if (direction.x >= pozice.x && direction.y >= pozice.y) {
                                pomPozice.y = pomPozice.y + 1;
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxM.x;
                                BeforeLast.y = pomDiryM.y;
                            } else if (direction.x <= pozice.x && direction.y >= pozice.y) {
                                pomPozice.y = pomPozice.y + 1;
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxP.x;
                                BeforeLast.y = pomDiryM.y;
                            } else if (direction.x <= pozice.x && direction.y <= pozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxP.x;
                                BeforeLast.y = pomDiryP.y;
                            } else if (direction.x >= pozice.x && direction.y <= pozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast.x = pomDirxM.x;
                                BeforeLast.y = pomDiryP.y;
                            }
                            if (mahmadan == false) {
                                break;
                            }
                        } while ((pomPozice.x != BeforeLast.x) && (pomPozice.y != BeforeLast.y));
                        if ((Math.abs(pozice.x - direction.x) == Math.abs(pozice.y - direction.y)) && mahmadan == true) {
                            side = false;
                            return true;
                        }
                    }
                    if ((direction.x == pozice.x && direction.y >= pozice.y)
                            || (direction.x == pozice.x && direction.y <= pozice.y)
                            || (direction.x <= pozice.x && direction.y == pozice.y)
                            || (direction.x >= pozice.x && direction.y == pozice.y)) {
                        do {
                            if ((direction.x == pomPozice.x && direction.y >= pomPozice.y)) {
                                pomPozice.y = pomPozice.y + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDiryM;
                            } else if (direction.x == pomPozice.x && direction.y <= pomPozice.y) {
                                pomPozice.y = pomPozice.y - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDiryP;
                            } else if (direction.x <= pomPozice.x && direction.y == pomPozice.y) {
                                pomPozice.x = pomPozice.x - 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDirxP;
                            } else if (direction.x >= pomPozice.x && direction.y == pomPozice.y) {
                                pomPozice.x = pomPozice.x + 1;
                                mahmadan = free.isWayClear(pomPozice, objectsForMovement);
                                BeforeLast = pomDirxM;
                            }
                            if (mahmadan == false) {
                                break;
                            }

                        } while ((pomPozice.x != BeforeLast.x) || (pomPozice.y != BeforeLast.y));
                        if (mahmadan == true) {
                            side = false;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }

            if (typ == Figures.Type.King) {
                if (direction.x == pozice.x && direction.y == pozice.y - 1
                        || direction.x == pozice.x - 1 && direction.y == pozice.y - 1
                        || direction.x == pozice.x && direction.y == pozice.y + 1
                        || direction.x == pozice.x - 1 && direction.y == pozice.y + 1
                        || direction.x == pozice.x + 1 && direction.y == pozice.y
                        || direction.x == pozice.x + 1 && direction.y == pozice.y - 1
                        || direction.x == pozice.x - 1 && direction.y == pozice.y
                        || direction.x == pozice.x + 1 && direction.y == pozice.y + 1) {
                    side = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKingTaken(ArrayList<ModelObject> objectsForMovement) {
        int count = 0;
        ArrayList<ModelObject> objectsForFree = new ArrayList<ModelObject>(objectsForMovement.size());
        for (ModelObject obj : objectsForMovement) {
            objectsForFree.add(obj);
        }
        for (ModelObject object : objectsForFree) {
            if (object instanceof Figures) {
                if (((Figures) object).getType() == Figures.Type.King) {
                    count++;
                }
            }
        }
        if (count == 2) {
            return false;
        } else {
            return true;
        }
    }

    public boolean GetSide() {
        return this.side;
    }

    public boolean PawnTaking() {
        return this.pawnTaking;
    }

}
