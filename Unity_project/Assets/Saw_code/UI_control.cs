using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using TMPro;

public class UI_control : MonoBehaviour
{
    public GameObject Panel;
    public TextMeshProUGUI Tp_time;
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
        // playtime += Time.deltaTime;
        // timeText.text = "Play time : " + playtime.ToString("N2") + "s";
            playtime -= Time.deltaTime;
            Tp_time.text = Mathf.Round(playtime) + "s";
            //Debug.Log(Mathf.Round(Move_key.count_time)); //시간확인

        }
    }
}
