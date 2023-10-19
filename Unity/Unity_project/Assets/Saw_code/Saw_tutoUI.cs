using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class Saw_tutoUI : MonoBehaviour
{
    
    public GameObject Panel;
    //public Text timeText;
    public TextMeshProUGUI Tptext;
    public static float playtime;
    public bool timestart;

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
    }

    public void Start_game()
    {
        Time.timeScale = 1;
        timestart = true;
        Panel.SetActive(false);
    }

    public void Timer() //시간제한
    {
        if (timestart)
        {
            playtime -= Time.deltaTime;
            //timeText.text = Mathf.Round(playtime) + "초";
            Tptext.text = Mathf.Round(playtime) + "s";
        }
    }

}
