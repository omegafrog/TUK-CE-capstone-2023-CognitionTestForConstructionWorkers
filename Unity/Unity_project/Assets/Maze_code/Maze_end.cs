using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Maze_end : MonoBehaviour
{
    public GameObject Panel;
    public Text count;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        UI_Counter();
    }

    public void UI_Counter()
    {
        count.text = Maze_move.Trigger_Count + "번";
    }
}
