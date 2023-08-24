using System.Collections;
using System.Collections.Generic;
using System.Threading;
using UnityEngine;
using UnityEngine.UI;

public class Conveyor_UI : MonoBehaviour
{
    public GameObject Panel;
    public Text timeText;
    public static float playtime;
    public bool timestart;
    public static bool UI_check;

    // Start is called before the first frame update
    void Start()
    {
        playtime = 0;
        Time.timeScale = 0;
        timestart = false;
        UI_check = false;
    }

    // Update is called once per frame
    void Update()
    {
        time_check();
    }

    public void game_start()
    {
        Time.timeScale = 1;
        timestart = true;
        Panel.SetActive(false);
        UI_check = true;

    }

    public void time_check()
    {
        if(timestart){
            playtime += Time.deltaTime;
            //timeText.text = "제한시간: " + Mathf.Round(playtime) + "초";
        }
    }
}
