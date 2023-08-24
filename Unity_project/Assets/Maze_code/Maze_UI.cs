using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Maze_UI : MonoBehaviour
{
    public GameObject Panel;
    public Text timeText;
    public static float playtime;
    public bool timestart;
    public Text CounterText;
    public static float Maze_timer; //시간측정

    // Start is called before the first frame update
    void Start()
    {
        Maze_timer = 0;
        playtime = 30;
        Time.timeScale = 0;
        timestart = false;
    }

    // Update is called once per frame
    void Update()
    {
        Timer();
        TG_Coundter();
    }

    public void Start_game()
    {
        Time.timeScale = 1;
        timestart = true;
        Panel.SetActive(false);
    }

    public void Timer() //시간제한 0초 까지만 측정
    {
        if (timestart)
        {
            if(playtime > 0) {
                Maze_timer += Time.deltaTime;
                playtime -= Time.deltaTime;
                timeText.text = "제한시간: " + Mathf.Round(playtime) + "초";
            }    
        }
    }

    public void TG_Coundter()
    {
        if (timestart)
        {
            CounterText.text = "실패횟수: " + Maze_move.Trigger_Count + "번";
        }
    }
}
