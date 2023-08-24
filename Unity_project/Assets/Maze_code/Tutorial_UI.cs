using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Tutorial_UI : MonoBehaviour
{
    public GameObject Panel;
    public Text timeText;
    
    public static float playtime;
    public bool timestart;
    
    public Text Tutorial_CT;

    // Start is called before the first frame update
    void Start()
    {
        playtime = 30;
        Time.timeScale = 0;
        timestart = false;
    }

    // Update is called once per frame
    void Update()
    {
        Timer();
        Tutorial_Counter();
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
            if (playtime > 0)
            {
                playtime -= Time.deltaTime;
                timeText.text = "제한시간: " + Mathf.Round(playtime) + "초";
            }
        }
    }

    public void Tutorial_Counter()
    {
        if (timestart)
        {
            Tutorial_CT.text = "실패횟수: " + Mazemove_tuto.Tutorial_Count + "번";
        }
    }
}
