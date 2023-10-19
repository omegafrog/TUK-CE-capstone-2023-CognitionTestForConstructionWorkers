using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class Conveyor_button : MonoBehaviour
{
    /// <summary>
    /// 반응속도 테스트
    /// 사용자의 반응속도 측정
    /// 잘못된 입력시 페널티 부여
    /// </summary>
    public float move_speed = 2.0f;
    public static float save_time; //반응시간 저장 변수값
    public static int err_count;
    public static int succes;

    private int ingame_succes;
    private int ingame_err;
    public static bool conveyor_result;

    public static bool yellow_button;
    public static bool blue_button;
    // Start is called before the first frame update
    void Start()
    {
        save_time = 0;
        err_count = 0;
        ingame_succes = 0;
        ingame_err = 0;
        conveyor_result = false;
        yellow_button = false;
    }
    // Update is called once per frame
    void Update()
    {
        if(Conveyor_UI.UI_check == true) Button_press();
        Game_end();

        //Debug.Log(save_time); //반응시간 저장확인
    }

    void Button_press()
    {
        if (Input.GetMouseButtonDown(0)) //버튼 입력확인
        {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;
            if(Physics.Raycast(ray, out hit))
            {
                if (hit.collider.CompareTag("red_button"))
                {
                    if (Conveyor_move.sr.material.color == Color.red)
                    {
                        //Debug.Log("succes");
                        succes++;
                        ingame_succes++;
                        save_time += Conveyor_move.timer; //반응속도 측정값 합산
                        StartCoroutine(buttonpress(1.0f));
                    }
                    else
                    {
                        StartCoroutine(buttonpress(1.0f));
                        //붉은색이 아닌 다른색의 오브젝트에 입력시 오류추가
                        //Debug.Log("error +1");
                        err_count++;
                        ingame_err++;
                    }
                }
                if (hit.collider.CompareTag("yellow_button")) //노란버튼 입력확인, 반응속도는 측정안함
                {
                    if (Conveyor_move.sr.material.color == Color.yellow)
                    {
                        yellow_button = true;
                        //UnityEngine.Debug.Log("yellow");
                    }
                    else
                    {
                        yellow_button = true;
                        err_count++;
                        ingame_err++;
                    }
                }
                if (hit.collider.CompareTag("blue_button")) //파란버튼 입력확인, 반응속도는 측정안함
                {
                    if (Conveyor_move.sr.material.color == Color.blue)
                    {
                        blue_button = true;
                    }
                    else
                    {
                        blue_button = true;
                        err_count++;
                        ingame_err++;
                    }
                }
            }

        
        }
    }

    void Game_end()
    {
        conveyor_result =true;
        if (ingame_err == 5)
        {
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            ingame_err = 0;
            ingame_succes = 0;
        }
        if (ingame_succes == 5)
        {
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
            ingame_err = 0;
            ingame_succes = 0;
        }
    }

    IEnumerator buttonpress(float delay) //버튼 입력시 효과
    {
        transform.localPosition = new Vector3(0f, 0.4f, 0f);
        while (delay > 0.5f)
        {
            delay -= Time.deltaTime;
            yield return new WaitForFixedUpdate();
        }
        transform.localPosition = new Vector3(0f, 0.5f, 0f);
    }
}
