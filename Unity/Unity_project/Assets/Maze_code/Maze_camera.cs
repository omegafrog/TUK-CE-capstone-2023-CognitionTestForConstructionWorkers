using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Maze_camera : MonoBehaviour
{
    void Update()
    {
        round2_move();
        round3_move();
    }

    private void round2_move()
    {
        if(Maze_move.Camera1 == true)
        {
            transform.position = new Vector3(45.59f, 11.09f, -0.02f);
            Maze_move.isStage2 = true;
            Maze_move.Camera1 = false;
        }
    }

    private void round3_move()
    {
        if(Maze_move.Camera2 == true)
        {
            transform.position = new Vector3(92.67f, 13.09f, -0.02f);
            Maze_move.isStage3 = true;
            Maze_move.Camera2 = false;
        }
    }
}
